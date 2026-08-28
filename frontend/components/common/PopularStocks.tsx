import { getPopularStocks } from "@/lib/api";

export default async function PopularStocks() {
  const data = await getPopularStocks();

  return (
    <div>
      <h3>인기 종목</h3>
      {data.results.map((stock, index) => (
        <a key={stock.stockCode} href={`/company/${stock.stockCode}`}>
          <span>{index + 1}</span>
          <span>{stock.corpName}</span>
          <span>{stock.currentPrice.toLocaleString()}원</span>
          <span>
            {stock.changeRate > 0 ? "▲" : "▼"} {Math.abs(stock.changeRate)}%
          </span>
        </a>
      ))}
    </div>
  );
}
