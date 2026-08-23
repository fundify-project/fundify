export interface CompanySearchResult {
  stockCode: string;
  corpName: string;
  market: string;
  industry: string;
  currentPrice: number;
  changeRate: number;
}

export interface CompanySearchResponse {
  results: CompanySearchResult[];
  totalCount: number;
}
