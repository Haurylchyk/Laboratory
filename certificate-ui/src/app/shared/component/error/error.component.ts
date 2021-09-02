import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  errorCode: number;
  errorMessage: string;

  constructor(
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.errorCode = this.activatedRoute.snapshot.queryParams.errorCode;
    this.errorMessage = this.activatedRoute.snapshot.queryParams.errorMessage;
    if (!this.errorCode) {
      this.errorCode = this.activatedRoute.snapshot.data.errorCode;
      this.errorMessage = this.activatedRoute.snapshot.data.errorMessage;
    }
  }
}
