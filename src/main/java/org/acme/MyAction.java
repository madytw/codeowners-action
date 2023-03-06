package org.acme;

import java.util.HashSet;

import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GitHub;

import io.quarkiverse.githubaction.Action;
import io.quarkiverse.githubaction.Commands;
import io.quarkiverse.githubapp.event.Push;

public class MyAction {

    @Action
    void onPushHandler(@Push GHEventPayload.Push pushPayload, GitHub gitHub, Commands commands) {
        var files = new HashSet<>();
        for (var commit : pushPayload.getCommits()) {
            try {
                files.addAll(commit.getAdded());
            } catch (Exception ex) {
            }
            try {
                files.addAll(commit.getModified());
            } catch (Exception ex) {
            }
            try {
                files.addAll(commit.getRemoved());
            } catch (Exception ex) {
            }

        }

        commands.appendJobSummary("Files size: " + files.size());
        files.forEach(file -> commands.appendJobSummary("File: " + file));
    }
}
