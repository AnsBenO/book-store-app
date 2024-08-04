import {
  patchState,
  signalStore,
  withComputed,
  withHooks,
  withMethods,
  withState,
} from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { Item } from '../types/book-item.type';
import { computed, inject } from '@angular/core';
import { pipe, switchMap, tap } from 'rxjs';
import { OrderService } from '../services/order.service';

type TCartState = {
  items: Item[];
};

const initialState: TCartState = {
  items: [],
};

export const CartStore = signalStore(
  withState<TCartState>(initialState),
  withComputed((store) => ({
    itemsCount: computed(() => store.items().length),
    totalPrice: computed(() =>
      store.items().reduce((prev, item) => prev + item.price * item.quantity, 0)
    ),
  })),
  withMethods((store, orderService = inject(OrderService)) => ({
    addItem(newItem: Item) {
      const updatedItems = [...store.items(), newItem];
      patchState(store, { items: updatedItems });
      localStorage.setItem('cartItems', JSON.stringify(updatedItems));
    },
    removeItem(code: string) {
      const updatedItems = store.items().filter((item) => item.code !== code);
      patchState(store, { items: updatedItems });
      localStorage.setItem('cartItems', JSON.stringify(updatedItems));
    },
    removeAll() {
      patchState(store, { items: [] });
    },
    loadItems: rxMethod<void>(
      pipe(
        switchMap(() => {
          return orderService
            .loadItemsFromLocalStorage()
            .pipe(
              tap((loadedItems) => patchState(store, { items: loadedItems }))
            );
        })
      )
    ),
  })),
  withHooks({
    onInit(store) {
      store.loadItems();
    },
  })
);
