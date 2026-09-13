import type { CompanyInfo } from "@/types/company";

interface CompanyHeaderProps {
  info: CompanyInfo;
}

export default function CompanyHeader({ info }: CompanyHeaderProps) {
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
    </div>
  );
}

// 453000000000000 → "453조"
function formatMarketCap(value: number) {
  if (value >= 1_0000_0000_0000) {
    return `${Math.round(value / 1_0000_0000_0000)}조`;
  }
  if (value >= 1_0000_0000) {
    return `${Math.round(value / 1_0000_0000)}억`;
  }
  return value.toLocaleString();
}
