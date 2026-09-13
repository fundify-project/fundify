import type { FinancialStatement } from "@/types/company";

interface FinancialTableProps {
  statements: FinancialStatement[];
}

export default function FinancialTable({ statements }: FinancialTableProps) {
  const rows = [
    { label: "매출액", key: "revenue" as const },
    { label: "영업이익", key: "operatingProfit" as const },
    { label: "당기순이익", key: "netIncome" as const },
    { label: "자산총계", key: "totalAssets" as const },
    { label: "부채총계", key: "totalLiabilities" as const },
  ];

  return (
    <div className="rounded-2xl border border-line bg-ink-2 px-6 py-5">
      <div className="mb-4 text-xs tracking-wide text-fg-3">
        재무제표 · 최근 5개년 (단위: 조원)
      </div>

      <table className="w-full">
        <thead>
          <tr className="border-b border-line">
            <th className="py-2.5 text-left text-xs font-medium text-fg-3">
              항목
            </th>
            {statements.map((s) => (
              <th
                key={s.fiscalYear}
                className="py-2.5 text-right text-xs font-medium text-fg-3"
              >
                {s.fiscalYear}
              </th>
            ))}
          </tr>
        </thead>

        <tbody>
          {rows.map((row) => (
            <tr key={row.key} className="border-b border-line/50 last:border-0">
              <td className="py-2.5 text-sm text-fg-2">{row.label}</td>
              {statements.map((s) => (
                <td
                  key={s.fiscalYear}
                  className="py-2.5 text-right text-sm font-medium text-fg"
                >
                  {toJo(s[row.key])}
                </td>
              ))}
            </tr>
          ))}

          <tr>
            <td className="py-2.5 text-sm text-fg-2">부채비율</td>
            {statements.map((s) => (
              <td
                key={s.fiscalYear}
                className="py-2.5 text-right text-sm font-medium text-fg"
              >
                {s.debtRatio}%
              </td>
            ))}
          </tr>
        </tbody>
      </table>
    </div>
  );
}

// 258935494000000 → "258.9"
function toJo(value: number) {
  return (value / 1_0000_0000_0000).toFixed(1);
}
