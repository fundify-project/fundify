import { CompanySearchResponse } from "@/types/company";

interface SearchPageProps {
  searchParams: Promise<{ keyword?: string }>;
}

export default async function SearchPage({ searchParams }: SearchPageProps) {
  const { keyword } = await searchParams;

  const res = await fetch(
    `http://localhost:8080/companies/search?keyword=${keyword}`,
  );
  const data: CompanySearchResponse = await res.json();

  return (
    <div>
      <h2>
        &quot;{keyword}&quot; 검색 결과 {data.totalCount}건
      </h2>
      {data.results.map((item) => (
        <div key={item.stockCode}>{item.corpName}</div>
      ))}
    </div>
  );
}
