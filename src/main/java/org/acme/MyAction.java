package org.acme;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GitHub;

import io.quarkiverse.githubaction.Action;
import io.quarkiverse.githubaction.Commands;
import io.quarkiverse.githubapp.event.Push;

public class MyAction {

    @Action
    void onPushHandler(@Push GHEventPayload.Push pushPayload, GitHub gitHub, Commands commands) {
        System.out.println("Commits size: " + Optional.ofNullable(pushPayload.getCommits()).map(List::size).orElse(0));
        System.out.println("Pusher email: " + Optional.ofNullable(pushPayload.getPusher()).map(GHEventPayload.Push.Pusher::getEmail).orElse("UNKNOWN"));
        var files = new HashSet<>();
        for (var commit : pushPayload.getCommits()) {
            System.out.println("Commit url: " + commit.getUrl());
            try {
                files.addAll(commit.getAdded());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                files.addAll(commit.getModified());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                files.addAll(commit.getRemoved());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

        commands.appendJobSummary("Files size: " + files.size());
        files.forEach(file -> commands.appendJobSummary("File: " + file));
    }
}
