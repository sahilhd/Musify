package com.artemifyMusicStudio.controller.queueServiceCommand;

import android.view.View;

import com.useCase.Queue;

public abstract class QueueServiceCommand implements View.OnClickListener {
    protected final Queue queueService;

    protected QueueServiceCommand(Queue queueService) {
        this.queueService = queueService;
    }

}
