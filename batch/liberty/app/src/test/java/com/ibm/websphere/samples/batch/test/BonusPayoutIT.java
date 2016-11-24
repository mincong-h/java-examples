/*
 * Copyright 2015 International Business Machines Corp.
 * 
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.websphere.samples.batch.test;

import static net.sf.expectit.filter.Filters.removeNonPrintable;
import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.eof;
import static net.sf.expectit.matcher.Matchers.regexp;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.security.acl.Group;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;

import javax.batch.runtime.BatchStatus;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import net.sf.expectit.Result;


public class BonusPayoutIT {

	// How long we wait for matching command line output in these tests
	private int COMMAND_LINE_WAIT_TIME_SECONDS = 25;
	
	private static enum SHELL_TYPE {UNIX, WINDOWS};
	private static SHELL_TYPE shellType;
	public static String WIN_CMD;
    public static final String BIN_SH = "/bin/sh";
	
	private Expect expect;
	private Process process;
	private String warName = System.getProperty("warName");
	private String wlpInstallDir = System.getProperty("wlp.install.dir");
	private String wlpUserDir = System.getProperty("wlp.user.dir");
	private String serverHost = System.getProperty("serverHost");
	private String httpsPort = System.getProperty("httpsPort");

	private String CORE_COMMAND_PARMS =
			"--batchManager=" +	serverHost + ":" + httpsPort + " " +
					"--trustSslCertificates --user=bob --password=bobpwd " + 			
					"--wait --pollingInterval_s=2 ";

	@BeforeClass
	public static void setupForPlatformShell() {
		
		if (new File(BIN_SH).canExecute()) {
			shellType = SHELL_TYPE.UNIX;
		} else {
			 WIN_CMD = System.getenv("COMSPEC");
			if (WIN_CMD != null && new File(WIN_CMD).canExecute()) {
				shellType = SHELL_TYPE.WINDOWS;
			}
			else {
				throw new IllegalStateException("Unable to find either /bin/sh or Windows command shells.");
			}
		}
	}

	@Before
	public void setup() throws IOException {
		ProcessBuilder builder = null;
		if (shellType == SHELL_TYPE.WINDOWS) {
			builder = new ProcessBuilder(WIN_CMD, "/Q");
		} else {
			builder = new ProcessBuilder(BIN_SH);
		}
		process = builder.start();
		expect = new ExpectBuilder()
				.withInputs(process.getInputStream(), process.getErrorStream())
				.withOutput(process.getOutputStream())
				.withInputFilters(removeNonPrintable())
				.withEchoInput(System.err)
				.withEchoOutput(System.out)
				.withTimeout(COMMAND_LINE_WAIT_TIME_SECONDS, TimeUnit.SECONDS)
				.build();
	}

	@After
	public void cleanup() throws IOException, InterruptedException {
		process.destroy();
		process.waitFor();
		expect.close();
	}

	@Test
	public void testRunToCompletion() throws Exception {
		String submitCmd = wlpInstallDir + "/bin/batchManager submit " +
				CORE_COMMAND_PARMS +   
				"--jobXMLName=BonusPayoutJob --applicationName=" + warName + " " +
				"--jobPropertiesFile=" + wlpUserDir + "/shared/resources/runToCompletionParms.txt ";

		expect.sendLine(submitCmd);

		assertJobTerminationStatus(BatchStatus.COMPLETED);

		expect.sendLine("exit");
		// expect the process to finish
		expect.expect(eof());
	}

	@Test
	public void testForceFailure() throws Exception {

		String submitCmd = wlpInstallDir + "/bin/batchManager submit " +
				CORE_COMMAND_PARMS +   
				"--jobXMLName=BonusPayoutJob --applicationName=" + warName + " " +
				"--jobPropertiesFile=" + wlpUserDir + "/shared/resources/forceFailureParms.txt ";

		expect.sendLine(submitCmd);

		long jobInstanceId = assertJobTerminationStatus(BatchStatus.FAILED);

		// It doesn't exactly force a failure every time, just after a certain # of records, but we've
		// gotten far enough to complete on the 1st restart.
		String restartCmd = wlpInstallDir + "/bin/batchManager restart " +
				CORE_COMMAND_PARMS + 
				"--jobInstanceId=" + jobInstanceId + " " +
				"--jobPropertiesFile=" + wlpUserDir + "/shared/resources/forceFailureParms.txt ";
		
		expect.sendLine(restartCmd);
		assertJobTerminationStatus(BatchStatus.COMPLETED);
		
		expect.sendLine("exit");
		// expect the process to finish
		expect.expect(eof());
	}

	/**
	 * @param batchStatus Expected status
	 * @return job instance ID
	 * @throws IOException
	 */
	private long assertJobTerminationStatus(BatchStatus batchStatus) throws IOException {
		// CWWKY0105I: Job {0} with instance ID {1} has finished. Batch status: {2}. Exit status: {3}
		Result res = expect.expect(regexp("CWWKY0105I: Job (.*) with instance ID (\\d+) has finished. Batch status: ([A-Z]+)"));	
		assertEquals("Job terminated, but in unexpected state", batchStatus.toString(), res.group(3));
		return Long.parseLong(res.group(2));
	}

	/*
	@Test
	@Ignore
	public void test() throws IOException, InterruptedException {

		System.out.println(expect.expect(contains(">")).getBefore());
		expect.sendLine("echo test-123");
		Result res = expect.expect(contains("test-123"));
		assertTrue(res.isSuccessful());
		assertFalse(res.getBefore().contains("echo"));
		assertTrue(expect.expect(contains(">")).isSuccessful());
		expect.sendLine("echo %cd%");
		System.err.println("pwd: " + expect.expect(contains("\n")).getBefore());
		expect.sendLine("exit");
		assertTrue(expect.expect(eof()).isSuccessful());
	}
	 */
}
