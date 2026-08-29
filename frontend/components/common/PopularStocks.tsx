import Link from "next/link";
import { getPopularStocks } from "@/lib/api";

export default async function PopularStocks() {
  const stocks = await getPopularStocks();

  return (
    <div className="mx-auto mt-10 w-full max-w-xl">
      <div className="mb-3 pl-1 text-left text-xs uppercase tracking-wider text-fg-3">
        인기 종목
      </div>

      <div className="flex flex-col gap-2">
        {stocks.map(
          (
            stock,
            index, // data.results → stocks
          ) => (
            <Link
              key={stock.stockCode}
              href={`/company/${stock.stockCode}`}
              className="flex items-center gap-4 rounded-xl border border-line bg-ink-2 px-4 py-3.5 transition hover:-translate-y-px hover:border-ink-3 hover:bg-ink-3"
            >
              <span className="w-4 text-sm font-semibold text-fg-3">
                {index + 1}
              </span>

              <span className="text-[15px] font-medium text-fg">
                {stock.corpName}
              </span>
              <span className="text-xs text-fg-3">{stock.stockCode}</span>

              <span className="ml-auto text-right">
                <span className="block text-[15px] font-semibold text-fg">
                  {stock.currentPrice.toLocaleString()}
                </span>
                <span
                  className={`block text-xs font-medium ${
                    stock.changeRate > 0 ? "text-mint" : "text-coral"
                  }`}
                >
                  {stock.changeRate > 0 ? "▲" : "▼"}{" "}
                  {Math.abs(stock.changeRate)}%
                </span>
              </span>
            </Link>
          ),
        )}
      </div>
    </div>
  );
}
