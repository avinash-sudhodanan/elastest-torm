import { SutModel } from './sut-model';
import { SutExecModel } from '../sut-exec/sutExec-model';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { ConfigurationService } from '../../config/configuration-service.service';
import 'rxjs/Rx';

@Injectable()
export class SutService {
  constructor(private http: Http, private configurationService: ConfigurationService) { }

  public getSuts() {
    let url = this.configurationService.configModel.hostApi + '/sut';
    return this.http.get(url)
      .map((response) => this.transformSutDataToDataTable(response.json()));
  }

  transformSutDataToDataTable(suts: any[]) {
    let sutsDataToTable: SutModel[] = [];
    for (let sut of suts) {
      sutsDataToTable.push(this.transformToSutmodel(sut));
    }
    return sutsDataToTable;
  }

  transformToSutmodel(sut: any) {
    let sutsDataToTable: SutModel;

    sutsDataToTable = new SutModel();
    sutsDataToTable.id = sut.id;
    sutsDataToTable.name = sut.name;
    sutsDataToTable.specification = sut.specification;
    sutsDataToTable.imageName = sut.imageName;
    sutsDataToTable.description = sut.description;
    sutsDataToTable.project = sut.project;

    return sutsDataToTable;
  }

  public getSut(id: number) {
    let url = this.configurationService.configModel.hostApi + '/sut/' + id;
    return this.http.get(url)
      .map(
      (response) => {
        let data: any = response.json();
        if (data !== undefined && data !== null) {
          return this.transformToSutmodel(data);
        }
        else {
          throw new Error('Empty response. SuT not exist or you don\'t have permissions to access it');
        }
      });
  }

  public createSut(sut: SutModel) {
    let url = this.configurationService.configModel.hostApi + '/sut';
    return this.http.post(url, sut)
      .map((response) => response.json());
  }

  public modifySut() {

  }

  public deleteSut(sut: SutModel) {
    let url = this.configurationService.configModel.hostApi + '/sut/' + sut.id;
    return this.http.delete(url)
      .map((response) => response.json());
  }

}
