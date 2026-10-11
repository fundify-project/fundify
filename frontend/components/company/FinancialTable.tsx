import type { FinancialStatement } from "@/types/company";

interface FinancialTableProps {
  statements: FinancialStatement[];
}

export default function FinancialTable({ statements }: FinancialTableProps) {
  const rows = [
    { label: "매출액", key: "revenue" as const },
    { label: "영업이익", key: "operatingProfit" as const },
    { label: "당기순이익", key: "netIncome" as const },
  ];

  if (statements.length === 0) {
    return (
      <div className="rounded-2xl border border-line bg-ink-2 px-6 py-8 text-center text-sm text-fg-3">
        재무제표 데이터가 없습니다
      </div>
    );
  }

  return (
    <div className="rounded-2xl border border-line bg-ink-2 px-6 py-5">
      <div className="mb-4 flex items-center">
        <span className="text-xs tracking-wide text-fg-3">
          재무제표 (단위: 조원)
        </span>
      </div>

      <div className="relative">
        <div className="-mx-6 overflow-x-auto px-6">
          <table className="w-full min-w-[440px]">
            <thead>
              <tr className="border-b border-line">
                <th className="py-2.5 pr-3 text-left text-xs font-medium text-fg-3">
                  항목
                </th>
                {statements.map((s) => (
                  <th
                    key={s.fiscalYear}
                    className="px-2.5 py-2.5 text-right text-xs font-medium text-fg-3"
                  >
                    {s.fiscalYear}
                  </th>
                ))}
              </tr>
            </thead>

            <tbody>
              {rows.map((row) => (
                <tr key={row.key} className="border-b border-line/50">
                  <td className="whitespace-nowrap py-3 pr-3 text-sm text-fg-2">
                    {row.label}
                  </td>
                  {statements.map((s) => (
                    <td
                      key={s.fiscalYear}
                      className="px-2.5 py-3 text-right text-[13px] font-medium text-fg sm:text-sm"
                    >
                      {toJo(s[row.key])}
                    </td>
                  ))}
                </tr>
              ))}

              <tr>
                <td className="whitespace-nowrap py-3 pr-3 text-sm text-fg-2">
                  부채비율
                </td>
                {statements.map((s) => (
                  <td
                    key={s.fiscalYear}
                    className="px-2.5 py-3 text-right text-[13px] font-medium text-fg sm:text-sm"
                  >
                    {s.debtRatio.toFixed(1)}%
                  </td>
                ))}
              </tr>
            </tbody>
          </table>
        </div>

        {/* 오른쪽 페이드 - 더 있다는 신호 */}
        <div className="pointer-events-none absolute inset-y-0 right-0 w-10 bg-gradient-to-l from-ink-2 to-transparent sm:hidden" />
      </div>
    </div>
  );
}

function toJo(value: number) {
  return (value / 1_0000_0000_0000).toFixed(1);
}
