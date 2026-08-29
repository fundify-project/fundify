//기업 요약 정보 
export interface CompanySummary {
  stockCode: string;
  corpName: string;
  currentPrice: number;
  changeRate: number;
}

// 검색 결과: 공통 + market, industry 추가
export interface CompanySearchResult extends CompanySummary {
  market: string;
  industry: string;
}

// 검색 API 응답
export interface CompanySearchResponse {
  results: CompanySearchResult[];
  totalCount: number;
}

// 인기종목 API 응답 
export type PopularStockResponse = CompanySummary[];
