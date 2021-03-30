jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MuonsachService } from '../service/muonsach.service';

import { MuonsachDeleteDialogComponent } from './muonsach-delete-dialog.component';

describe('Component Tests', () => {
  describe('Muonsach Management Delete Component', () => {
    let comp: MuonsachDeleteDialogComponent;
    let fixture: ComponentFixture<MuonsachDeleteDialogComponent>;
    let service: MuonsachService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MuonsachDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(MuonsachDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MuonsachDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(MuonsachService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
