import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'cost'
})
export class CostPipe implements PipeTransform {

  constructor(
  ) {
  }
  transform(cost: string, ...args: any[]): string {
  return cost + ' (US dollars)';
  }

}
