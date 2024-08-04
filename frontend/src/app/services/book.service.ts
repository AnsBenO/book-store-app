import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BooksPage } from '../types/books-page.type';
import { BOOKS_API_URL } from '../app';

@Injectable({
  providedIn: 'root',
})
export class BookService {
  http = inject(HttpClient);

  getAllBooks() {
    return this.http.get<BooksPage>(`${BOOKS_API_URL}/books`);
  }
}
