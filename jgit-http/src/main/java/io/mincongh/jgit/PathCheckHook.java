package io.mincongh.jgit;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceiveCommand.Result;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author Mincong Huang */
public class PathCheckHook implements PreReceiveHook {

  private static final Logger logger = LoggerFactory.getLogger(PathCheckHook.class);

  private final Pattern pattern;

  public PathCheckHook(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public void onPreReceive(ReceivePack rp, Collection<ReceiveCommand> commands) {
    Repository repo = rp.getRepository();

    try (RevWalk revWalk = new RevWalk(repo)) {
      for (ReceiveCommand cmd : commands) {
        switch (cmd.getType()) {
          case CREATE:
            cmd.setResult(Result.REJECTED_NOCREATE);
            break;
          case DELETE:
            cmd.setResult(Result.REJECTED_NODELETE);
            break;
          case UPDATE_NONFASTFORWARD:
            cmd.setResult(Result.REJECTED_NONFASTFORWARD);
            break;
          case UPDATE:
            RevCommit newCommit = revWalk.parseCommit(cmd.getNewId());
            RevCommit oldCommit = revWalk.parseCommit(cmd.getOldId());
            revWalk.markStart(newCommit);
            revWalk.markUninteresting(oldCommit);

            RevCommit curr = revWalk.next();
            RevCommit prev = revWalk.next();
            boolean allMatched = true;
            while (prev != null) {
              logger.info("curr: {}", curr.abbreviate(7).name(), curr.getFullMessage());
              logger.info("prev: {}", prev.abbreviate(7).name(), prev.getFullMessage());
              // diff: curr..prev
              try (TreeWalk treeWalk = new TreeWalk(repo)) {
                treeWalk.addTree(prev.getTree());
                treeWalk.addTree(curr.getTree());
                treeWalk.setRecursive(true);
                allMatched = allMatched && isMatched(DiffEntry.scan(treeWalk));
              }
              curr = prev;
              prev = revWalk.next();
            }
            // diff: curr..old
            try (TreeWalk treeWalk = new TreeWalk(repo)) {
              logger.info("curr: {}", curr.abbreviate(7).name());
              logger.info("prev: {}", oldCommit.abbreviate(7).name());
              treeWalk.addTree(oldCommit.getTree());
              treeWalk.addTree(curr.getTree());
              treeWalk.setRecursive(true);
              allMatched = allMatched && isMatched(DiffEntry.scan(treeWalk));
            }
            if (!allMatched) {
              cmd.setResult(Result.REJECTED_OTHER_REASON, "Only changes of 'doc/*' is allowed");
            }
            break;
          default:
            break;
        }
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e.getCause());
    }
  }

  private boolean isMatched(Iterable<DiffEntry> entries) {
    boolean allMatched = true;
    for (DiffEntry e : entries) {
      boolean matched = false;
      // oldPath
      switch (e.getChangeType()) {
        case MODIFY:
        case DELETE:
        case COPY:
        case RENAME:
          matched = isMatched(e.getOldPath());
          break;
        default:
          break;
      }
      // newPath
      switch (e.getChangeType()) {
        case ADD:
        case MODIFY:
        case COPY:
        case RENAME:
          matched = isMatched(e.getNewPath());
          break;
        default:
          break;
      }
      if (matched) {
        logger.info("Accept: {}", e);
      } else {
        logger.info("Refuse: {}", e);
      }
      allMatched = allMatched && matched;
    }
    return allMatched;
  }

  private boolean isMatched(String path) {
    return pattern.matcher(path).matches();
  }
}
