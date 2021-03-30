import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PhongdungsachService } from '../service/phongdungsach.service';

import { PhongdungsachComponent } from './phongdungsach.component';

describe('Component Tests', () => {
  describe('Phongdungsach Management Component', () => {
    let comp: PhongdungsachComponent;
    let fixture: ComponentFixture<PhongdungsachComponent>;
    let service: PhongdungsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongdungsachComponent],
      })
        .overrideTemplate(PhongdungsachComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongdungsachComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PhongdungsachService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.phongdungsaches?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
