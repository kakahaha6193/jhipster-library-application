import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DocgiaService } from '../service/docgia.service';

import { DocgiaComponent } from './docgia.component';

describe('Component Tests', () => {
  describe('Docgia Management Component', () => {
    let comp: DocgiaComponent;
    let fixture: ComponentFixture<DocgiaComponent>;
    let service: DocgiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DocgiaComponent],
      })
        .overrideTemplate(DocgiaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocgiaComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DocgiaService);

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
      expect(comp.docgias?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
