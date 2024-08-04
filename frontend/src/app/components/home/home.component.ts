import { CommonModule } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  OnInit,
  WritableSignal,
  inject,
  signal,
} from '@angular/core';
import { BookService } from '../../services/book.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BooksPage } from '../../types/books-page.type';
import { patchState, signalState } from '@ngrx/signals';
import { Book } from '../../types/book.type';
import { BookDetailComponent } from '../book-detail/book-detail.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, BookDetailComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit {
  bookService = inject(BookService);
  destroy = inject(DestroyRef);
  pageState = signalState<BooksPage>({
    data: [],
    totalElements: 0,
    pageNumber: 0,
    totalPages: 0,
    isFirst: false,
    isLast: false,
    hasNext: false,
    hasPrevious: false,
  });

  showModal: WritableSignal<boolean> = signal(false);
  selectedBook: Book | null = null;

  ngOnInit(): void {
    this.getBookPage();
  }

  private getBookPage() {
    this.bookService
      .getAllBooks()
      .pipe(takeUntilDestroyed(this.destroy))
      .subscribe((page) => patchState(this.pageState, page));
  }

  openModal(book: Book) {
    this.selectedBook = book;
    this.showModal.set(true);
  }

  closeModal() {
    this.showModal.set(false);
    this.selectedBook = null;
  }
}
