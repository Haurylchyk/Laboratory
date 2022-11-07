import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CostPipe } from './pipe/cost.pipe';
import { ConfirmationDialogComponent } from './component/confirmation-dialog/confirmation-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import { ErrorComponent } from './component/error/error.component';



@NgModule({
  declarations: [CostPipe, ConfirmationDialogComponent, ErrorComponent],
    exports: [
        CostPipe,
        ConfirmationDialogComponent
    ],
  imports: [
    CommonModule,
    MatDialogModule
  ]
})
export class SharedModule { }
