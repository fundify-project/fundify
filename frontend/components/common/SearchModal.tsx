"use client";

import { useState } from "react";
import { searchCompanies } from "@/lib/api";
import type { CompanySearchResult } from "@/types/company";

interface SearchModalProps {
  onClose: () => void;
  onSelect: (stockCode: string) => void;
}

export default function SearchModal({ onClose, onSelect }: SearchModalProps) {
  const [keyword, setKeyword] = useState("");
  const [results, setResults] = useState<CompanySearchResult[]>([]);
  const [searched, setSearched] = useState(false);

  const handleSearch = async () => {
    const trimmed = keyword.trim();
    if (!trimmed) return;

    try {
      const data = await searchCompanies(trimmed);
      setResults(data.results);
      setSearched(true);
    } catch (err) {
      console.error("검색 실패:", err);
    }
  };

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 px-6"
      onClick={onClose}
    >
      <div
        className="w-full max-w-md rounded-2xl border border-line bg-ink-2 p-6"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="mb-4 flex items-center">
          <h2 className="text-lg font-semibold text-fg">기업 추가</h2>
          <button
            onClick={onClose}
            className="ml-auto text-lg text-fg-3 transition hover:text-fg"
          >
            ✕
          </button>
        </div>

        <div className="flex gap-2">
          <input
            type="text"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            placeholder="기업명 또는 종목코드"
            autoFocus
            className="flex-1 rounded-xl border border-line bg-ink px-4 py-2.5 text-sm text-fg outline-none transition placeholder:text-fg-3 focus:border-mint-dim"
          />
          <button
            onClick={handleSearch}
            className="rounded-xl bg-mint px-4 py-2.5 text-sm font-semibold text-[#062018] transition hover:bg-[#4ee0b2]"
          >
            검색
          </button>
        </div>

        <div className="mt-4 max-h-80 overflow-y-auto">
          {searched && results.length === 0 ? (
            <p className="py-8 text-center text-sm text-fg-3">
              검색 결과가 없습니다
            </p>
          ) : (
            <div className="flex flex-col gap-2">
              {results.map((item) => (
                <button
                  key={item.stockCode}
                  onClick={() => {
                    onSelect(item.stockCode);
                    onClose();
                  }}
                  className="flex items-center rounded-xl border border-line bg-ink px-4 py-3 text-left transition hover:border-mint-dim"
                >
                  <div>
                    <div className="text-sm font-medium text-fg">
                      {item.corpName}
                    </div>
                    <div className="mt-0.5 text-xs text-fg-3">
                      {item.stockCode}
                    </div>
                  </div>
                </button>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
