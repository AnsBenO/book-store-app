import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BooksPage } from '../types/books-page.type';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BookService {
  http = inject(HttpClient);

  getAllBooks(page?: number) {
    console.log(page);

    return page
      ? this.http.get<BooksPage>(
          `${environment.booksApiUrl}/books?page=${page}`
        )
      : this.http.get<BooksPage>(`${environment.booksApiUrl}/books`);
  }
}
