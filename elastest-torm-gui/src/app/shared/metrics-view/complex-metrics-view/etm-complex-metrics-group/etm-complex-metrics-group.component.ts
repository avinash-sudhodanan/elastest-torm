import { ElastestRabbitmqService } from '../../../services/elastest-rabbitmq.service';
import { Subject } from 'rxjs/Rx';
import { ComplexMetricsViewComponent } from '../complex-metrics-view.component';
import { TJobModel } from '../../../../elastest-etm/tjob/tjob-model';
import { ElastestESService } from '../../../services/elastest-es.service';
import { ESRabComplexMetricsModel } from '../models/es-rab-complex-metrics-model';
import { TJobExecModel } from '../../../../elastest-etm/tjob-exec/tjobExec-model';
import { Component, Input, OnInit, Output, QueryList, ViewChildren, EventEmitter } from '@angular/core';
import { Subscription } from 'rxjs/Rx';

@Component({
  selector: 'etm-complex-metrics-group',
  templateUrl: './etm-complex-metrics-group.component.html',
  styleUrls: ['./etm-complex-metrics-group.component.scss']
})
export class EtmComplexMetricsGroupComponent implements OnInit {
  @ViewChildren(ComplexMetricsViewComponent) complexMetricsViewComponents: QueryList<ComplexMetricsViewComponent>;

  @Input()
  public live: boolean;

  // Metrics Chart
  allInOneMetrics: ESRabComplexMetricsModel;
  metricsList: ESRabComplexMetricsModel[] = [];
  groupedMetricsList: ESRabComplexMetricsModel[][] = [];

  loaded: boolean = false;

  testMetricsSubscription: Subscription;
  sutMetricsSubscription: Subscription;

  // TimeLine Observable
  @Output()
  timelineObs = new EventEmitter<any>();

  @Output()
  hoverObs = new EventEmitter<any>();

  @Output()
  leaveObs = new EventEmitter<any>();

  constructor(
    private elastestESService: ElastestESService,
    private elastestRabbitmqService: ElastestRabbitmqService,
  ) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    if (this.live) {
      this.testMetricsSubscription = this.elastestRabbitmqService.testMetrics$
        .subscribe((data) => this.updateMetricsData(data));

      this.sutMetricsSubscription = this.elastestRabbitmqService.sutMetrics$
        .subscribe((data) => this.updateMetricsData(data));
    }
  }


  initMetricsView(tJob: TJobModel, tJobExec: TJobExecModel) {
    if (tJob.execDashboardConfigModel.showComplexMetrics) {
      this.allInOneMetrics = new ESRabComplexMetricsModel(this.elastestESService);
      this.allInOneMetrics.name = 'All Metrics';
      this.allInOneMetrics.hidePrevBtn = !this.live;
      this.allInOneMetrics.metricsIndex = tJobExec.logIndex;
      if (!this.live) {
        this.allInOneMetrics.getAllMetrics();
      }
    }
    for (let metric of tJob.execDashboardConfigModel.allMetricsFields.fieldsList) {
      if (metric.activated) {
        let individualMetrics: ESRabComplexMetricsModel = new ESRabComplexMetricsModel(this.elastestESService);
        individualMetrics.name = metric.type + ' ' + metric.subtype;

        individualMetrics.activateAllMatchesByNameSuffix(metric.name);
        individualMetrics.hidePrevBtn = !this.live;
        individualMetrics.metricsIndex = tJobExec.logIndex;
        if (!this.live) {
          individualMetrics.getAllMetrics();
        }

        this.metricsList.push(individualMetrics);
      }
    }
    this.groupedMetricsList = this.createGroupedArray(this.metricsList, 2);
  }

  createGroupedArray(arr, chunkSize) {
    let groups = [], i;
    for (i = 0; i < arr.length; i += chunkSize) {
      groups.push(arr.slice(i, i + chunkSize));
    }
    return groups;
  }

  updateMetricsData(data: any) {
    for (let group of this.groupedMetricsList) {
      for (let metric of group) {
        metric.updateData(data);
      }
    }

    this.allInOneMetrics.updateData(data);
  }

  ngAfterViewChecked() {
    if (!this.loaded) {
      this.subscribeToEvents();
    }
  }

  subscribeToEvents() {
    this.loaded = this.complexMetricsViewComponents.toArray() && this.complexMetricsViewComponents.toArray().length > 0;
    if (this.loaded) {
      this.complexMetricsViewComponents.forEach(
        (element) => {
          element.getTimelineSubscription().subscribe(
            (data) => {
              this.updateTimeline(data);
              this.timelineObs.next(data);
            }
          );

          element.getHoverSubscription().subscribe(
            (data) => {
              this.hoverCharts(data);
              this.hoverObs.next(data.value);
            }
          )

          element.getLeaveSubscription().subscribe(
            (data) => {
              this.leaveCharts();
              this.leaveObs.next();
            }
          )
        }
      );
    }
  }

  updateTimeline(domain) {
    this.complexMetricsViewComponents.forEach(
      (element) => {
        element.updateDomain(domain);
      }
    );
  }

  hoverCharts(item) {
    this.complexMetricsViewComponents.forEach(
      (element) => {
        element.hoverCharts(item);
      }
    );
  }

  leaveCharts() {
    this.complexMetricsViewComponents.forEach(
      (element) => {
        element.leaveCharts();
      }
    );
  }

}
