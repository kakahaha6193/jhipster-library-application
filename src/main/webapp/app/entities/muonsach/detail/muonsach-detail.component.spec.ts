import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MuonsachDetailComponent } from './muonsach-detail.component';

describe('Component Tests', () => {
  describe('Muonsach Management Detail Component', () => {
    let comp: MuonsachDetailComponent;
    let fixture: ComponentFixture<MuonsachDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MuonsachDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ muonsach: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MuonsachDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MuonsachDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load muonsach on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.muonsach).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
