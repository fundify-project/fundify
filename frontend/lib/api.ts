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

// TODO: 백엔드 detail API 완성되면 실제 호출로 교체
const DUMMY_DETAIL: CompanyDetailResponse = {
  info: {
    stockCode: "005930",
    corpCode: "00126380",
    corpName: "삼성전자",
    market: "KOSPI",
    industry: "전기전자",
    ceoName: "한종희",
    marketCap: 453000000000000,
  },
  financials: {
    fsDiv: "CFS",
    statements: [
      {
        fiscalYear: 2023,
        revenue: 258935494000000,
        operatingProfit: 6566976000000,
        netIncome: 15487100000000,
        totalAssets: 455905980000000,
        totalLiabilities: 92228115000000,
        totalEquity: 363677865000000,
        debtRatio: 25.4,
      },
      {
        fiscalYear: 2022,
        revenue: 302231360000000,
        operatingProfit: 43377000000000,
        netIncome: 55654000000000,
        totalAssets: 448424507000000,
        totalLiabilities: 93674903000000,
        totalEquity: 354749604000000,
        debtRatio: 26.4,
      },
      {
        fiscalYear: 2021,
        revenue: 279604770000000,
        operatingProfit: 51633856000000,
        netIncome: 39907450000000,
        totalAssets: 426621158000000,
        totalLiabilities: 121721227000000,
        totalEquity: 304899931000000,
        debtRatio: 39.9,
      },
      {
        fiscalYear: 2020,
        revenue: 236806988000000,
        operatingProfit: 35993876000000,
        netIncome: 26407832000000,
        totalAssets: 378235718000000,
        totalLiabilities: 102287702000000,
        totalEquity: 275948016000000,
        debtRatio: 37.1,
      },
      {
        fiscalYear: 2019,
        revenue: 230400881000000,
        operatingProfit: 27768509000000,
        netIncome: 21738865000000,
        totalAssets: 352564497000000,
        totalLiabilities: 89684076000000,
        totalEquity: 262880421000000,
        debtRatio: 34.1,
      },
    ],
  },
  metrics: {
    baseDate: "2026-08-23",
    items: [
      {
        name: "PER",
        value: 12.5,
        industryAvg: 18.2,
        category: "밸류에이션",
        evaluation: "저평가",
      },
      {
        name: "PBR",
        value: 1.2,
        industryAvg: 1.5,
        category: "밸류에이션",
        evaluation: "저평가",
      },
      {
        name: "ROE",
        value: 15.0,
        industryAvg: 9.0,
        category: "수익성",
        evaluation: "우수",
      },
      {
        name: "부채비율",
        value: 45.0,
        industryAvg: 80.0,
        category: "안정성",
        evaluation: "양호",
      },
    ],
  },
};

export async function getCompanyDetail(
  stockCode: string,
): Promise<CompanyDetailResponse> {
  // TODO: 백엔드 완성되면 아래 주석 해제하고 더미 제거
  // const res = await fetch(`${BASE_URL}/companies/${stockCode}/detail`);
  // if (!res.ok) throw new Error("기업 상세 조회 실패");
  // return res.json();
  return DUMMY_DETAIL;
}

export async function getPriceHistory(
  stockCode: string,
  period = "1Y",
): Promise<PriceHistoryResponse> {
  // TODO: 백엔드 완성되면 실제 호출로 교체
  const count = period === "1M" ? 20 : period === "6M" ? 60 : 120;
  const prices = Array.from({ length: count }, (_, i) => ({
    date: `2026-${String((i % 12) + 1).padStart(2, "0")}-01`,
    close: 68000 + Math.round(Math.sin(i / 5) * 5000 + i * 60),
    volume: 9000000 + i * 50000,
  }));
  return { period, prices };
}