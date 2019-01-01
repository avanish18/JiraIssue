
package com.create.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicVotes;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MyJiraClient {

	private String username;
	private String password;
	private String jiraUrl;
	public JiraRestClient restClient;

	public MyJiraClient(String username, String password, String jiraUrl) {
		this.username = username;
		this.password = password;
		this.jiraUrl = jiraUrl;
		this.restClient = getJiraRestClient();
	}

	public String createIssue(String projectKey, Long issueType, String issueSummary) {

		IssueRestClient issueClient = restClient.getIssueClient();

		IssueInput newIssue = new IssueInputBuilder(projectKey, issueType, issueSummary).build();

		return issueClient.createIssue(newIssue).claim().getKey();
	}

	public void updateIssueDescription(String issueKey, String newDescription) {
		IssueInput input = new IssueInputBuilder().setDescription(newDescription).build();
		restClient.getIssueClient().updateIssue(issueKey, input).claim();
	}

	public Issue getIssue(String issueKey) {
		return restClient.getIssueClient().getIssue(issueKey).claim();
	}

	public void addComment(Issue issue, String commentBody) {
		restClient.getIssueClient().addComment(issue.getCommentsUri(), Comment.valueOf(commentBody));
	}

	public JiraRestClient getJiraRestClient() {
		return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(getJiraUri(), this.username,
				this.password);
	}

	public URI getJiraUri() {
		return URI.create(this.jiraUrl);
	}
}