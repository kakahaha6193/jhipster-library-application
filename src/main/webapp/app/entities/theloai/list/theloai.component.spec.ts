import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TheloaiService } from '../service/theloai.service';

import { TheloaiComponent } from './theloai.component';

describe('Component Tests', () => {
  describe('Theloai Management Component', () => {
    let comp: TheloaiComponent;
    let fixture: ComponentFixture<TheloaiComponent>;
    let service: TheloaiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TheloaiComponent],
      })
        .overrideTemplate(TheloaiComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TheloaiComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TheloaiService);

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
      expect(comp.theloais?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
