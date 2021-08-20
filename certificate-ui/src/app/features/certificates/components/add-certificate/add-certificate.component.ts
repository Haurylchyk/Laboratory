import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {GiftCertificate} from '../../../../core/model/gift-certificate';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {
  certificateForm: FormGroup;
  tags: string[] = [];
  tagName: string;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private giftCertificateService: GiftCertificateService,
  ) {
  }

  ngOnInit(): void {
    this.certificateForm = this.formBuilder.group({
      name: ['', Validators.required],
      price: ['', Validators.required],
      duration: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.nullValidator],
    });
  }

  onSubmit(): void {
    const giftCertificate: GiftCertificate = {
      id: null,
      name: this.certificateForm.get('name').value,
      description: this.certificateForm.get('description').value,
      price: this.certificateForm.get('price').value,
      duration: this.certificateForm.get('duration').value,
      createDate: null,
      lastUpdateDate: null,
      tagNames: this.tags
    };
    this.giftCertificateService.createCertificate(giftCertificate).subscribe(certificate => {
      this.router.navigateByUrl('/certificates');
    });
  }

  redirectToAllCertificates(): void {
    this.router.navigateByUrl(`/certificates`);
  }

  addCertificateCategory(): void {
    this.tags.push(this.tagName);
    this.tagName = '';
  }

  deleteCertificateCategory(tagName: any): void {
    this.tags = this.tags.filter(t => t !== tagName);
  }
}
