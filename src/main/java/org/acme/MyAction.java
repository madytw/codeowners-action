package org.acme;

import java.util.stream.Collectors;

import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GitHub;

import io.quarkiverse.githubaction.Action;
import io.quarkiverse.githubaction.Commands;
import io.quarkiverse.githubapp.event.Push;

public class MyAction {

    @Action
    void onPushHandler(@Push GHEventPayload.Push pushPayload, GitHub gitHub, Commands commands) {
        var files = pushPayload.getCommits().stream().flatMap(c -> c.getAdded().stream()).collect(Collectors.toSet());
        pushPayload.getCommits().stream().flatMap(c -> c.getRemoved().stream()).forEach(files::add);
        pushPayload.getCommits().stream().flatMap(c -> c.getModified().stream()).forEach(files::add);

        commands.debug("Files size: " + files.size());
        files.forEach(file -> commands.debug("File: " + file));
    }
}
