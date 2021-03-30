import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NhaxuatbanDetailComponent } from './nhaxuatban-detail.component';

describe('Component Tests', () => {
  describe('Nhaxuatban Management Detail Component', () => {
    let comp: NhaxuatbanDetailComponent;
    let fixture: ComponentFixture<NhaxuatbanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NhaxuatbanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nhaxuatban: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NhaxuatbanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhaxuatbanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nhaxuatban on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nhaxuatban).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
