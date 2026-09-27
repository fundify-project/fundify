"use client";

import { useEffect, useState } from "react";
import { useCompareStore } from "@/store/useCompareStore";
import { getCompanyDetail } from "@/lib/api";
import CompareTable from "@/components/compare/CompareTable";
import SearchModal from "@/components/common/SearchModal";
import type { CompanyDetailResponse } from "@/types/company";

export default function ComparePage() {
  const stockCodes = useCompareStore((s) => s.stockCodes);
  const add = useCompareStore((s) => s.add);
  const remove = useCompareStore((s) => s.remove);
  const clear = useCompareStore((s) => s.clear);

  const [companies, setCompanies] = useState<CompanyDetailResponse[] | null>(
    null,
  );
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    if (stockCodes.length === 0) return;

    let cancelled = false;

    Promise.all(stockCodes.map((code) => getCompanyDetail(code)))
      .then((results) => {
        if (!cancelled) setCompanies(results);
      })
      .catch((err) => console.error("비교 데이터 조회 실패:", err));

    return () => {
      cancelled = true;
    };
  }, [stockCodes]);

  const isEmpty = stockCodes.length === 0;
  const isLoading = !isEmpty && companies === null;
  const isFull = stockCodes.length >= 3;

  return (
    <main className="px-6 py-10">
      <div className="mx-auto max-w-4xl">
        <div className="mb-6 flex items-center gap-3">
          <h1 className="text-2xl font-semibold text-fg">기업 비교</h1>

          <button
            onClick={() => setModalOpen(true)}
            disabled={isFull}
            className={`ml-auto rounded-lg border px-3.5 py-2 text-sm transition ${
              isFull
                ? "cursor-not-allowed border-line text-fg-3 opacity-50"
                : "border-line text-fg-2 hover:border-mint-dim hover:text-fg"
            }`}
          >
            {isFull ? "최대 3개" : "+ 기업 추가"}
          </button>

          {!isEmpty && (
            <button
              onClick={clear}
              className="text-sm text-fg-3 transition hover:text-fg-2"
            >
              전체 삭제
            </button>
          )}
        </div>

        {isEmpty ? (
          <div className="rounded-2xl border border-line bg-ink-2 px-6 py-16 text-center">
            <p className="text-fg-2">비교할 기업을 추가해보세요</p>
            <button
              onClick={() => setModalOpen(true)}
              className="mt-5 rounded-xl bg-mint px-5 py-3 text-sm font-semibold text-[#062018] transition hover:bg-[#4ee0b2]"
            >
              + 기업 추가
            </button>
          </div>
        ) : isLoading ? (
          <div className="rounded-2xl border border-line bg-ink-2 px-6 py-16 text-center text-sm text-fg-3">
            불러오는 중...
          </div>
        ) : (
          <>
            <CompareTable companies={companies ?? []} onRemove={remove} />
            <p className="mt-4 text-center text-xs text-fg-3">
              <span className="inline-block h-2 w-2 rounded-full bg-mint"></span>{" "}
              = 항목별 우위 기업
            </p>
          </>
        )}
      </div>

      {modalOpen && (
        <SearchModal onClose={() => setModalOpen(false)} onSelect={add} />
      )}
    </main>
  );
}
