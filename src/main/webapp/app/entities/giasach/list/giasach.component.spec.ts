import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { GiasachService } from '../service/giasach.service';

import { GiasachComponent } from './giasach.component';

describe('Component Tests', () => {
  describe('Giasach Management Component', () => {
    let comp: GiasachComponent;
    let fixture: ComponentFixture<GiasachComponent>;
    let service: GiasachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GiasachComponent],
      })
        .overrideTemplate(GiasachComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GiasachComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(GiasachService);

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
      expect(comp.giasaches?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
