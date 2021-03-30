import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CuonsachService } from '../service/cuonsach.service';

import { CuonsachComponent } from './cuonsach.component';

describe('Component Tests', () => {
  describe('Cuonsach Management Component', () => {
    let comp: CuonsachComponent;
    let fixture: ComponentFixture<CuonsachComponent>;
    let service: CuonsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CuonsachComponent],
      })
        .overrideTemplate(CuonsachComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CuonsachComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CuonsachService);

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
      expect(comp.cuonsaches?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
