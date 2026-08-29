import Link from "next/link";
import { searchCompanies } from "@/lib/api";

interface SearchPageProps {
  searchParams: Promise<{ keyword?: string }>;
}

export default async function SearchPage({ searchParams }: SearchPageProps) {
  const { keyword } = await searchParams;
  const data = await searchCompanies(keyword ?? "");

  return (
    <main className="min-h-screen bg-ink px-6 py-10">
      <div className="mx-auto max-w-2xl">
        <h2 className="mb-5 text-lg font-semibold text-fg">
          &quot;<span className="text-mint">{keyword}</span>&quot; 검색 결과{" "}
          <span className="font-normal text-fg-3">{data.totalCount}건</span>
        </h2>

        <div className="flex flex-col gap-2">
          {data.results.map((item) => (
            <Link
              key={item.stockCode}
              href={`/company/${item.stockCode}`}
              className="flex items-center gap-4 rounded-xl border border-line bg-ink-2 px-5 py-4 transition hover:-translate-y-px hover:border-mint-dim"
            >
              <div>
                <div className="font-semibold text-fg">{item.corpName}</div>
                <div className="mt-0.5 text-xs text-fg-3">
                  {item.stockCode}
                  {item.industry && ` · ${item.industry}`}
                </div>
              </div>
              <div className="ml-auto text-right">
                <div className="font-semibold text-fg">
                  {item.currentPrice?.toLocaleString() ?? "-"}
                </div>
                <div
                  className={`text-xs font-medium ${
                    (item.changeRate ?? 0) > 0 ? "text-mint" : "text-coral"
                  }`}
                >
                  {(item.changeRate ?? 0) > 0 ? "▲" : "▼"}{" "}
                  {Math.abs(item.changeRate ?? 0)}%
                </div>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </main>
  );
}
