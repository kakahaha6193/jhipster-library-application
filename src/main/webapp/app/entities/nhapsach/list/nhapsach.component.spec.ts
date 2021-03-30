import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NhapsachService } from '../service/nhapsach.service';

import { NhapsachComponent } from './nhapsach.component';

describe('Component Tests', () => {
  describe('Nhapsach Management Component', () => {
    let comp: NhapsachComponent;
    let fixture: ComponentFixture<NhapsachComponent>;
    let service: NhapsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhapsachComponent],
      })
        .overrideTemplate(NhapsachComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhapsachComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NhapsachService);

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
      expect(comp.nhapsaches?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
