import { CompanySearchResponse, PopularStockResponse } from "@/types/company";

const BASE_URL = "http://localhost:8080";

// 기업 검색
export async function searchCompanies(
  keyword: string,
): Promise<CompanySearchResponse> {
  const res = await fetch(
    `${BASE_URL}/companies/search?keyword=${encodeURIComponent(keyword)}`,
  );
  if (!res.ok) throw new Error("기업 검색 실패");
  return res.json();
}

// 인기 종목 (더미)
const DUMMY_POPULAR: PopularStockResponse = {
  results: [
    {
      stockCode: "005930",
      corpName: "삼성전자",
      currentPrice: 74000,
      changeRate: 1.2,
    },
    {
      stockCode: "000660",
      corpName: "SK하이닉스",
      currentPrice: 183000,
      changeRate: 0.8,
    },
    {
      stockCode: "005380",
      corpName: "현대차",
      currentPrice: 245000,
      changeRate: -0.3,
    },
    {
      stockCode: "035420",
      corpName: "NAVER",
      currentPrice: 182000,
      changeRate: 2.1,
    },
    {
      stockCode: "207940",
      corpName: "삼성바이오로직스",
      currentPrice: 781000,
      changeRate: 1.5,
    },
  ],
};

export async function getPopularStocks(): Promise<PopularStockResponse> {
  // TODO: 백엔드 인기종목 API 완성되면 아래 주석 해제하고 더미 제거
  // const res = await fetch(`${BASE_URL}/companies/popular`);
  // if (!res.ok) throw new Error("인기 종목 조회 실패");
  // return res.json();

  return DUMMY_POPULAR;
}
