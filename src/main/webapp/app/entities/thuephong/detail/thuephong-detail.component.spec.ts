import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThuephongDetailComponent } from './thuephong-detail.component';

describe('Component Tests', () => {
  describe('Thuephong Management Detail Component', () => {
    let comp: ThuephongDetailComponent;
    let fixture: ComponentFixture<ThuephongDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ThuephongDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ thuephong: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ThuephongDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThuephongDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load thuephong on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.thuephong).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
