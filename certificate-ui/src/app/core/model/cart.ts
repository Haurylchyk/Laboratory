import {GiftCertificate} from './gift-certificate';

export class Cart {
  userId: number;
  certificateList: GiftCertificate[] = [];
  count = 0;
  cost = 0;
}
