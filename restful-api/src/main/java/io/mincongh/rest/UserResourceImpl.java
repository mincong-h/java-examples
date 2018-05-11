package io.mincongh.rest;

import io.mincongh.rest.dto.User;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.Response;

/**
 * Default implementation of user endpoint.
 *
 * @author Mincong Huang
 */
@SuppressWarnings("unused")
public class UserResourceImpl implements UserResource {

  private AtomicInteger id = new AtomicInteger(0);

  private Map<Integer, User> users = new HashMap<>();

  public UserResourceImpl() {
    users.put(-1, new User(-1, "Foo", 30));
  }

  @Override
  public User createUser(User user) {
    user.setId(id.getAndIncrement());
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public User getUser(int id) {
    if (users.containsKey(id)) {
      return users.get(id);
    }
    throw new UserNotFoundException("Cannot find user (id=" + id + ")");
  }

  @Override
  public Response ping() {
    return Response.ok().build();
  }
}
