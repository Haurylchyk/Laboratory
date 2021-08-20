export class GiftCertificate {
  id: number;
  name: string;
  description: string;
  price: number;
  createDate: Date;
  lastUpdateDate: Date;
  duration: number;
  tagNames: string[] = [];
}
