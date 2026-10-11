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

// 기업 기본정보
export interface CompanyInfo {
  stockCode: string;
  corpCode: string;
  corpName: string;
  market: string;
  industry: string;
  ceoName: string;
  marketCap: number;
}

// 재무제표 한 해
export interface FinancialStatement {
  fiscalYear: number;
  revenue: number;
  operatingProfit: number;
  netIncome: number;
  totalAssets: number;
  totalLiabilities: number;
  totalEquity: number;
  debtRatio: number;
}

// 투자 지표 하나
export interface Metric {
  name: string;
  value: number;
  industryAvg: number;
  category: string;
  evaluation: string;
}

// 상세 API 응답
export interface CompanyDetailResponse {
  info: CompanyInfo;
  financials: FinancialStatement[]; // 배열로
  metrics: Metric[];
}

// 시세 차트
export interface PricePoint {
  date: string;
  close: number;
  volume: number;
}

export interface PriceHistoryResponse {
  period: string;
  prices: PricePoint[];
}

export interface CurrentPrice {
  stockCode: string;
  currentPrice: number;
  changeRate: number;
  volume: number;
  updatedAt: string;
}