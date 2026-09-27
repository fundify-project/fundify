import { create } from "zustand";

interface CompareState {
  stockCodes: string[];
  add: (stockCode: string) => void;
  remove: (stockCode: string) => void;
  clear: () => void;
  has: (stockCode: string) => boolean;
}

const MAX_COMPARE = 3;

export const useCompareStore = create<CompareState>((set, get) => ({
  stockCodes: [],

  add: (stockCode) =>
    set((state) => {
      if (state.stockCodes.includes(stockCode)) return state;
      if (state.stockCodes.length >= MAX_COMPARE) return state;
      return { stockCodes: [...state.stockCodes, stockCode] };
    }),

  remove: (stockCode) =>
    set((state) => ({
      stockCodes: state.stockCodes.filter((c) => c !== stockCode),
    })),

  clear: () => set({ stockCodes: [] }),

  has: (stockCode) => get().stockCodes.includes(stockCode),
}));
