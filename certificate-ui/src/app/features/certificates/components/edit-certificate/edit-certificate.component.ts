import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {GiftCertificate} from '../../../../core/model/gift-certificate';

@Component({
  selector: 'app-edit-certificate',
  templateUrl: './edit-certificate.component.html',
  styleUrls: ['./edit-certificate.component.css']
})
export class EditCertificateComponent implements OnInit {
  certificateForm: FormGroup;
  tags: string[] = [];
  tagName: string;
  private certificateId: number;

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private giftCertificateService: GiftCertificateService
  ) {
  }

  ngOnInit(): void {
    this.certificateForm = this.formBuilder.group({
      id: ['', Validators.nullValidator],
      name: ['', Validators.required],
      price: ['', Validators.required],
      duration: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.nullValidator],
    });

    this.certificateId = this.activatedRoute.snapshot.params.id;

    this.giftCertificateService.getCertificateById(this.certificateId)
      .subscribe(
        (certificate) => {
          console.log(certificate);
          this.certificateForm.patchValue({
            id: certificate.id,
            name: certificate.name,
            price: certificate.price,
            duration: certificate.duration,
            description: certificate.description,
          });
          this.tags = certificate.tagNames;
        });
  }

  onSubmit(): void {
    const giftCertificate: GiftCertificate = {
      id: this.certificateForm.get('id').value,
      name: this.certificateForm.get('name').value,
      description: this.certificateForm.get('description').value,
      price: this.certificateForm.get('price').value,
      duration: this.certificateForm.get('duration').value,
      createDate: null,
      lastUpdateDate: null,
      tagNames: this.tags,
    };
    console.log(giftCertificate);
    this.giftCertificateService.updateCertificate(giftCertificate, this.certificateId).subscribe(certificate => {
      console.log(certificate);
      this.router.navigateByUrl('/certificates');
    }, error => {
      console.log(error);
    });
  }

  addCertificateCategory(): void {
    this.tags.push(this.tagName);
    this.tagName = '';
  }

  deleteCertificateCategory(tagName: any): void {
    this.tags = this.tags.filter(t => t !== tagName);
  }

  redirectToAllCertificates(): void {
    this.router.navigateByUrl(`/certificates`);
  }
}
