import CompareButton from "@/components/company/CompareButton";
import type { CompanyInfo, CurrentPrice } from "@/types/company";

interface CompanyHeaderProps {
  info: CompanyInfo;
  price: CurrentPrice;
}

export default function CompanyHeader({ info, price }: CompanyHeaderProps) {
  const isUp = price.changeRate > 0;
  const isDown = price.changeRate < 0;
  const priceColor = isUp ? "text-up" : isDown ? "text-down" : "text-fg";

  return (
    <div className="rounded-2xl bg-ink-2 px-6 py-6 sm:px-7 sm:py-7">
      <div className="flex items-start">
        <div className="min-w-0">
          {/* 기업명 */}
          <h1 className="text-xl font-bold tracking-tight text-fg sm:text-2xl">
            {info.corpName}
          </h1>

          {/* 가격 */}
          <div
            className={`mt-3 text-3xl font-bold tracking-tight sm:text-4xl ${priceColor}`}
          >
            {price.currentPrice.toLocaleString()}
            <span className="text-2xl sm:text-3xl">원</span>
          </div>

          {/* 등락률 */}
          <div
            className={`mt-1.5 text-base font-semibold sm:text-lg ${priceColor}`}
          >
            {isUp ? "▲" : isDown ? "▼" : ""} {isUp ? "+" : ""}
            {price.changeRate}%
          </div>

          {/* 부가 정보 */}
          <div className="mt-4 text-sm text-fg-2">
            {info.stockCode} · {info.market} · 시가총액{" "}
            {formatMarketCap(info.marketCap)}
          </div>

          {/* 기준일 */}
          <div className="mt-1 text-xs text-fg-3">{price.updatedAt} 기준</div>
        </div>

        {/* 비교 버튼 */}
        <div className="ml-auto flex-none">
          <CompareButton stockCode={info.stockCode} />
        </div>
      </div>
    </div>
  );
}

function formatMarketCap(value: number) {
  if (value >= 10000) {
    return `${Math.round(value / 10000).toLocaleString()}조`;
  }
  return `${value.toLocaleString()}억`;
}
