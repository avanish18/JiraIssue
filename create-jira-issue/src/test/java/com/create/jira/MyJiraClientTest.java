package com.create.jira;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.Issue;

import com.create.jira.MyJiraClient;

import junit.framework.TestCase;

public class MyJiraClientTest extends TestCase {

	String username = "avanish.yadav18@gmail.com";
	String password = "jira12345";
	String jiraUrl = "https://avanishjira.atlassian.net";
	String projectKey = "JIR";
	long issueType = 10005L;
	MyJiraClient myJiraClient;

	@Override

	protected void setUp() throws Exception {

		System.out.println("Setting it up!");

		myJiraClient = new MyJiraClient(username, password, jiraUrl);

	}

	public void testCreateIssue() {

		try {
			System.out.println("Running: testCreateIssue");
			int i = 5 / 0;

		} catch (Exception e) {
			e.printStackTrace();
			final String issueKey = myJiraClient.createIssue(projectKey, issueType, "Issue created with full details");
			Issue issue = myJiraClient.getIssue(issueKey);
			myJiraClient.addComment(issue, "Issue Created with initial Comment");
			myJiraClient.updateIssueDescription(issueKey, Arrays.toString(e.getStackTrace()));

		}

	}

	@Override
	protected void tearDown() throws Exception {
		System.out.println("Running: tearDown");

		myJiraClient.restClient.close();

	}
}
