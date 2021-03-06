import { TJobExecModel } from '../../tjob-exec/tjobExec-model';
import { Router } from '@angular/router';
import { TJobExecService } from '../../tjob-exec/tjobExec.service';
import { TJobModel } from '../tjob-model';
import { Component, Inject, OnInit } from '@angular/core';
import { MD_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'run-tjob-modal',
  templateUrl: './run-tjob-modal.component.html',
  styleUrls: ['./run-tjob-modal.component.scss']
})
export class RunTJobModalComponent implements OnInit {

  constructor(
    private tJobExecService: TJobExecService,
    private router: Router,
    @Inject(MD_DIALOG_DATA) public tJob: TJobModel
  ) { }

  ngOnInit() {
  }

  runTJob() {
    this.tJobExecService.runTJob(this.tJob.id, this.tJob.parameters)
      .subscribe(
      (tjobExecution: TJobExecModel) => {
        this.router.navigate(['/projects', this.tJob.project.id, 'tjob', this.tJob.id, 'tjob-exec', tjobExecution.id, 'dashboard']);
      },
      (error) => console.error('Error:' + error),
    );
  }
}
