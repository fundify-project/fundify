import {
  CompanySearchResponse,
  PopularStockResponse,
  CurrentPrice,
} from "@/types/company";

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
): Promise<CompanyDetailResponse | null> {
  const res = await fetch(`${BASE_URL}/companies/${stockCode}/detail`);
  if (!res.ok) return null;
  return res.json();
}

export async function getPriceHistory(
  stockCode: string,
  period = "1M"
): Promise<PriceHistoryResponse> {
  const res = await fetch(
    `${BASE_URL}/companies/${stockCode}/prices?period=${period}`
  );
  if (!res.ok) throw new Error("시세 조회 실패");
  return res.json();
}

export async function getCurrentPrice(
  stockCode: string,
): Promise<CurrentPrice> {
  const res = await fetch(`${BASE_URL}/companies/${stockCode}/price`);
  if (!res.ok) throw new Error("현재가 조회 실패");
  return res.json();
}