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
