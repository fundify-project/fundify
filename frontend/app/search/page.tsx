import { searchCompanies } from "@/lib/api";

interface SearchPageProps {
  searchParams: Promise<{ keyword?: string }>;
}

export default async function SearchPage({ searchParams }: SearchPageProps) {
  const { keyword } = await searchParams;
  const data = await searchCompanies(keyword ?? "");

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
