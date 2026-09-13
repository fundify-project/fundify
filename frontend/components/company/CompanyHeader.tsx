import type { CompanyInfo, CurrentPrice } from "@/types/company";

interface CompanyHeaderProps {
  info: CompanyInfo;
  price: CurrentPrice;
}

export default function CompanyHeader({ info, price }: CompanyHeaderProps) {
  const isUp = price.changeRate > 0;
  const isDown = price.changeRate < 0;

  return (
    <div className="flex items-start gap-5 rounded-2xl bg-ink-2 px-7 py-6">
      <div className="flex h-14 w-14 flex-none items-center justify-center rounded-xl bg-ink-3 text-xl font-bold text-mint">
        {info.corpName.charAt(0)}
      </div>

      <div>
        <div className="flex items-baseline gap-2">
          <span className="text-2xl font-semibold tracking-tight text-fg">
            {info.corpName}
          </span>
          <span className="text-sm text-fg-3">{info.stockCode}</span>
        </div>
        <div className="mt-1.5 text-sm text-fg-2">
          {info.market} · {info.industry} · 시가총액{" "}
          {formatMarketCap(info.marketCap)}
        </div>
      </div>

      {/* 현재가 영역 추가 */}
      <div className="ml-auto text-right">
        <div className="text-2xl font-bold tracking-tight text-fg">
          {price.currentPrice.toLocaleString()}
          <span className="ml-1 text-base font-normal text-fg-3">원</span>
        </div>
        <div
          className={`mt-1 text-sm font-medium ${
            isUp ? "text-up" : isDown ? "text-down" : "text-fg-3"
          }`}
        >
          {isUp ? "▲" : isDown ? "▼" : ""} {isUp ? "+" : ""}
          {price.changeRate}%
        </div>
      </div>
    </div>
  );
}

// 억원 단위로 들어옴
function formatMarketCap(value: number) {
  if (value >= 10000) {
    return `${Math.round(value / 10000).toLocaleString()}조`;
  }
  return `${value.toLocaleString()}억`;
}
