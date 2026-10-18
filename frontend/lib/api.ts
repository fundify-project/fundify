import type {
  CompanySearchResponse,
  PopularStockResponse,
  CompanyDetailResponse,
  CurrentPrice,
  PriceHistoryResponse,
} from "@/types/company";

const BASE_URL =
  typeof window === "undefined"
    ? process.env.API_URL
    : "/api";

// 기업 검색
export async function searchCompanies(
  keyword: string,
): Promise<CompanySearchResponse> {
  try {
    const res = await fetch(
      `${BASE_URL}/companies/search?keyword=${encodeURIComponent(keyword)}`,
    );
    if (!res.ok) return { results: [], totalCount: 0 };
    return res.json();
  } catch {
    return { results: [], totalCount: 0 };
  }
}

// 인기 종목
export async function getPopularStocks(): Promise<PopularStockResponse> {
  try {
    const res = await fetch(`${BASE_URL}/companies/popular`);
    if (!res.ok) return [];
    return res.json();
  } catch {
    return [];
  }
}

// 기업 상세
export async function getCompanyDetail(
  stockCode: string,
): Promise<CompanyDetailResponse | null> {
  try {
    const res = await fetch(`${BASE_URL}/companies/${stockCode}/detail`);
    if (!res.ok) return null;
    return res.json();
  } catch {
    return null;
  }
}

// 현재가
export async function getCurrentPrice(
  stockCode: string,
): Promise<CurrentPrice | null> {
  try {
    const res = await fetch(`${BASE_URL}/companies/${stockCode}/price`);
    if (!res.ok) return null;
    return res.json();
  } catch {
    return null;
  }
}

// 시세 차트
export async function getPriceHistory(
  stockCode: string,
  period = "1M",
): Promise<PriceHistoryResponse> {
  try {
    const res = await fetch(
      `${BASE_URL}/companies/${stockCode}/prices?period=${period}`,
    );
    if (!res.ok) return { period, prices: [] };
    return res.json();
  } catch {
    return { period, prices: [] };
  }
}
