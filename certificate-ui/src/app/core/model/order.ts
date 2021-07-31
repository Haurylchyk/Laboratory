import {User} from './user';
import {GiftCertificate} from './gift-certificate';

export class Order {
  id: number;
  userLogin: User;
  giftCertificateList: GiftCertificate [];
  cost: number;
  orderDate: Date;
}
