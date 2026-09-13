import { CompanySearchResponse, PopularStockResponse } from "@/types/company";

const BASE_URL = "http://localhost:8080";

// 기업 검색
export async function searchCompanies(
  keyword: string,
): Promise<CompanySearchResponse> {
  const res = await fetch(
    `${BASE_URL}/companies/search?keyword=${encodeURIComponent(keyword)}&size=50`,
  );
  if (!res.ok) throw new Error("기업 검색 실패");
  return res.json();
}

export async function getPopularStocks(): Promise<PopularStockResponse> {
  const res = await fetch(`${BASE_URL}/companies/popular`);
  if (!res.ok) throw new Error("인기 종목 조회 실패");
  return res.json();
}

import type {
  CompanyDetailResponse,
  PriceHistoryResponse,
} from "@/types/company";


export async function getCompanyDetail(
  stockCode: string,
): Promise<CompanyDetailResponse> {
  const res = await fetch(`${BASE_URL}/companies/${stockCode}/detail`);
  if (!res.ok) throw new Error("기업 상세 조회 실패");
 return res.json();
}

export async function getPriceHistory(
  stockCode: string,
  period = "1Y",
): Promise<PriceHistoryResponse> {
  const count = period === "1M" ? 20 : period === "6M" ? 60 : 120;
  const prices = Array.from({ length: count }, (_, i) => ({
    date: `2026-${String((i % 12) + 1).padStart(2, "0")}-01`,
    close: 68000 + Math.round(Math.sin(i / 5) * 5000 + i * 60),
    volume: 9000000 + i * 50000,
  }));
  return { period, prices };
}